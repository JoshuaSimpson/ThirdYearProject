class AccessPointsController < ApplicationController
  before_action :set_access_point, only: [:show, :edit, :update, :destroy]

  # GET /access_points
  # GET /access_points.json
  def index
    @access_points = AccessPoint.find( :all, :order => "time ASC")

    @access_list = AccessPoint.all
    respond_to do |format|
      format.html
      format.csv { send_data @access_list.to_csv }
    end
  end

  # GET /access_points/1
  # GET /access_points/1.json
  def show
  end

  # GET /access_points/new
  def new
    @access_point = AccessPoint.new
  end

  # GET /access_points/1/edit
  def edit
  end

  # POST /access_points
  # POST /access_points.json
  def create
    @access_point = AccessPoint.new(access_point_params)

    respond_to do |format|
      if @access_point.save
        format.html { redirect_to @access_point, notice: 'Access point was successfully created.' }
        format.json { render action: 'show', status: :created, location: @access_point }
      else
        format.html { render action: 'new' }
        format.json { render json: @access_point.errors, status: :unprocessable_entity }
      end
    end
  end

  # PATCH/PUT /access_points/1
  # PATCH/PUT /access_points/1.json
  def update
    respond_to do |format|
      if @access_point.update(access_point_params)
        format.html { redirect_to @access_point, notice: 'Access point was successfully updated.' }
        format.json { head :no_content }
      else
        format.html { render action: 'edit' }
        format.json { render json: @access_point.errors, status: :unprocessable_entity }
      end
    end
  end

  # DELETE /access_points/1
  # DELETE /access_points/1.json
  def destroy
    @access_point.destroy
    respond_to do |format|
      format.html { redirect_to access_points_url }
      format.json { head :no_content }
    end
  end

  private
    # Use callbacks to share common setup or constraints between actions.
    def set_access_point
      @access_point = AccessPoint.find(params[:id])
    end

    # Never trust parameters from the scary internet, only allow the white list through.
    def access_point_params
      params.require(:access_point).permit(:mac, :location, :time, :notes)
    end
end
