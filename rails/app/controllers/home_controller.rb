class HomeController < ApplicationController
  before_filter :authenticate_user!
  def index
  	@access_points = AccessPoint.find( :all, :order => "time DESC", :limit => 5)

  	puts user_signed_in?
  	@lastweek = Location.last_week_locations_count
  end
end
