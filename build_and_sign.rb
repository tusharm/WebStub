#!/usr/bin/env ruby

require 'nokogiri'

@pom = File.open('./pom.xml')

begin
  doc = Nokogiri::XML(@pom)
  version = doc.xpath('/x:project/x:version', 'x' => 'http://maven.apache.org/POM/4.0.0')[0].content

  success = system "mvn clean package ; cp pom.xml target/web-stub-#{version}.pom ; mvn source:jar ; mvn javadoc:jar"
  exit 1 unless (success)

  [ "web-stub-#{version}.pom", "web-stub-#{version}.jar", "web-stub-#{version}-sources.jar", "web-stub-#{version}-javadoc.jar"].each do |f|
    %x{ gpg2 -ab target/#{f} }
  end
rescue => e
  puts "Error while processing POM: #{e.message}"
ensure
  @pom.close unless @pom.nil?
end
