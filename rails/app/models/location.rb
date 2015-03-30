class Location < ActiveRecord::Base

	def self.to_csv
		CSV.generate do |csv|
			csv << column_names
			all.each do |loc| 
				csv << loc.attributes.values_at(*column_names)
			end
		end
	end

	def self.last_week_locations
		where("time > ?", (DateTime.now - 7.days).beginning_of_day().group(:loc).count
	end
end
