class Location < ActiveRecord::Base

	def self.to_csv
		CSV.generate do |csv|
			csv << column_names
			all.each do |loc| 
				csv << loc.attributes.values_at(*column_names)
			end
		end
	end

	def self.last_week_locations_count
		where("time > ?", (DateTime.now - 7.days).beginning_of_day()).group(:loc).count
	end

	def self.last_week_locations
		where("time > ?", (DateTime.now - 7.days).beginning_of_day()).group(:loc)
	end

	def self.get_location_count(location)
		summary = Location.where("loc == ? AND time > ?", location, (DateTime.now - 7.days)).group(:time).count
		past_week = ((Date.today - 7).. Date.today)

		past_week = past_week.map {|d| d.strftime "%y-%m-%d" }

		summary = Hash[summary.map{ |k, v| [k.strftime("%y-%m-%d"), v]}]

		final_result =Hash[*past_week.map(&:to_s).product([0]).flatten].merge Hash[*summary.flatten]

		final_result.to_a
    end
end