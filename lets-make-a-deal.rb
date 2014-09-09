#!/usr/bin/env ruby
# Encoding: utf-8
# Cloud Foundry Java Buildpack
# Copyright (c) 2013 the original author or authors.
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#      http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.

require 'highline/import'
require 'json'
require 'rest_client'

GAME_ROOT = 'http://localhost:8080/games'
# GAME_ROOT = 'http://micro-service.cfapps.io/games'

def get_link_location(source, rel)
  payload = (source.is_a? String) ? JSON.parse(RestClient.get(source)) : source

  location = nil
  payload['links'].each do |link|
    location = link['href'] if link['rel'] == rel
    next if location.nil?
  end

  location
end

def get_doors(location)
  doors = {}

  JSON.parse(RestClient.get(location))['content'].each do |door|
    doors[get_link_location(door, 'self')] = {'status' => door['status'], 'content' => door['content']}
  end

  doors
end

def print_current_state(game_location, doors)
  puts
  puts "Game Status:    #{JSON.parse(RestClient.get(game_location))['status']}"

  index = -1
  doors.each_value { |door| puts "Door #{index += 1} Status:  #{door['status']}/#{door['content']}" }
end

########################################################################################################################

game_location = RestClient.post(GAME_ROOT, nil).headers[:location]
doors_location = get_link_location game_location, 'doors'

puts "Let's Make A Deal!"

doors = get_doors doors_location
print_current_state game_location, doors
selection = ask('Select a door:  ', Integer) { |q| q.in = 0..2 }
RestClient.put doors.keys[selection], {'status' => 'SELECTED'}.to_json, {:content_type => 'application/json'}

doors = get_doors doors_location
print_current_state game_location, doors
selection = ask('Open a door:    ', Integer) { |q| q.in = 0..2 }
RestClient.put doors.keys[selection], {'status' => 'OPENED'}.to_json, {:content_type => 'application/json'}

doors = get_doors doors_location
print_current_state game_location, doors

RestClient.delete game_location
