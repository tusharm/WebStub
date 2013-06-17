#!/usr/bin/env ruby

@pom = File.open('./pom.xml')

def pom_version
  require 'nokogiri'
  doc = Nokogiri::XML(@pom)
  doc.xpath('/x:project/x:version', 'x' => 'http://maven.apache.org/POM/4.0.0')[0].content
end

def sign
  @artifacts.each do |file|
    %x{ gpg2 -ab target/#{file} }
  end

  exit 1 if ($?.exitstatus != 0)
end

def bundle
  system "cd target; jar cvf bundle.jar #{@artifacts.collect { |f| [f, f + '.asc'] }.flatten.join(' ') }"
end

begin
  version = pom_version

  if version.include?('SNAPSHOT')
    raise "The artifact is not released. Aborting.."
    exit 1
  end

  success = system "mvn clean package ; cp pom.xml target/web-stub-#{version}.pom ; mvn source:jar ; mvn javadoc:jar"
  exit 1 unless success

  @artifacts = [
      "web-stub-#{version}.pom",
      "web-stub-#{version}.jar",
      "web-stub-#{version}-sources.jar",
      "web-stub-#{version}-javadoc.jar"
  ]

  sign
  bundle
rescue => e
  puts "Error signing artifacts: #{e.message}"
ensure
  @pom.close unless @pom.nil?
end

