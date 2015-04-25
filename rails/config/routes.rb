Myapp::Application.routes.draw do
  devise_for :users
  resources :locations

  get "home/index"

  root to: 'home#index'

  post '/dbs', to: 'manager#upload'
  get '/dbs/get', to: 'manager#download'
  get '/help', to: 'help#index'

  resources :access_points, only: [:index, :show, :new, :edit, :create, :destroy, :patch]
end
