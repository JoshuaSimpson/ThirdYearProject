class DbFile < ActiveRecord::Base

	def self.save(upload)
		name = "LOSTANDFOUND"
		directory = "public/dbs"

		path = File.join(directory,name)

		File.open(path, "wb") { |f| f.write(upload['tempfile'].read)}
	end
end
