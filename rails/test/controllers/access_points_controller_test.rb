require 'test_helper'

class AccessPointsControllerTest < ActionController::TestCase
  setup do
    @access_point = access_points(:one)
  end

  test "should get index" do
    get :index
    assert_response :success
    assert_not_nil assigns(:access_points)
  end

  test "should get new" do
    get :new
    assert_response :success
  end

  test "should create access_point" do
    assert_difference('AccessPoint.count') do
      post :create, access_point: { location: @access_point.location, mac: @access_point.mac, notes: @access_point.notes, time: @access_point.time }
    end

    assert_redirected_to access_point_path(assigns(:access_point))
  end

  test "should show access_point" do
    get :show, id: @access_point
    assert_response :success
  end

  test "should get edit" do
    get :edit, id: @access_point
    assert_response :success
  end

  test "should update access_point" do
    patch :update, id: @access_point, access_point: { location: @access_point.location, mac: @access_point.mac, notes: @access_point.notes, time: @access_point.time }
    assert_redirected_to access_point_path(assigns(:access_point))
  end

  test "should destroy access_point" do
    assert_difference('AccessPoint.count', -1) do
      delete :destroy, id: @access_point
    end

    assert_redirected_to access_points_path
  end
end
