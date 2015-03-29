Myapp::Application.routes.draw do
  resources :locations

  get "home/index"

  root to: 'home#index'

  post '/dbs', to: 'manager#upload'
  get '/dbs/get', to: 'manager#download'

  resources :access_points, only: [:index, :show, :new, :create, :destroy, :edit, :patch]
end