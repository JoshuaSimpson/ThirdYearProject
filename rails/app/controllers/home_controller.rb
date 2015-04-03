class HomeController < ApplicationController
  def index
  	@access_points = AccessPoint.find( :all, :order => "time DESC", :limit => 5)

  	@lastweek = Location.last_week_locations_count
  end
end
