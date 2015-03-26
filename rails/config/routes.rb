Myapp::Application.routes.draw do
  resources :locations

  get "home/index"

  root to: 'home#index'

  resources :access_points, only: [:index, :show, :new, :create, :destroy, :edit, :patch]
end
