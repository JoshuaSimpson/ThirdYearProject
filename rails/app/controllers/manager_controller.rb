class ManagerController < ApplicationController

	def upload
		uploaded_stuff = params[:DB]
		File.open(Rails.root.join('public', 'dbs', uploaded_stuff.original_filename), "wb") do |file| file.write(uploaded_stuff.read) 
		end
	end
end
