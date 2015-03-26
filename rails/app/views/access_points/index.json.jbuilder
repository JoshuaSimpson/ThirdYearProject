json.array!(@access_points) do |access_point|
  json.extract! access_point, :id, :mac, :location, :time, :notes
  json.url access_point_url(access_point, format: :json)
end
