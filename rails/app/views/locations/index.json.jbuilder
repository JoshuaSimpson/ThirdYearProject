json.array!(@locations) do |location|
  json.extract! location, :id, :loc, :time
  json.url location_url(location, format: :json)
end
