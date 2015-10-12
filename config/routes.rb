Rails.application.routes.draw do
  root 'welcome#index'
  namespace :api, defaults: {format: 'json'} do
    namespace :v1 do
      get 'patient/' => 'patients#get_patient'
      get 'patient/dosages' => 'patients#get_dosages'
      post 'patient/quiz_feedback' => 'quiz_feedbacks#create'
      post 'patient/medication_feedback' => 'feedbacks#create'
    end
  end
  devise_for :users#, :controllers => { registrations: "user/registrations" }
  resources :treatments
  resources :patients do
    get :autocomplete_treatment_name, :on => :collection
  end
  resources :dosages  
  
  get 'patients/:id/qr_code' => "patients#generate_qr", :as => 'patient_qr_download'
  get 'patients/:id/mail_doctor' => "patients#mail_to_doctor", :as => 'email_to_doctor'

  # The priority is based upon order of creation: first created -> highest priority.
  # See how all your routes lay out with "rake routes".

  # You can have the root of your site routed with "root"
  # root 'welcome#index'

  # Example of regular route:
  #   get 'products/:id' => 'catalog#view'

  # Example of named route that can be invoked with purchase_url(id: product.id)
  #   get 'products/:id/purchase' => 'catalog#purchase', as: :purchase

  # Example resource route (maps HTTP verbs to controller actions automatically):
  #   resources :products

  # Example resource route with options:
  #   resources :products do
  #     member do
  #       get 'short'
  #       post 'toggle'
  #     end
  #
  #     collection do
  #       get 'sold'
  #     end
  #   end

  # Example resource route with sub-resources:
  #   resources :products do
  #     resources :comments, :sales
  #     resource :seller
  #   end

  # Example resource route with more complex sub-resources:
  #   resources :products do
  #     resources :comments
  #     resources :sales do
  #       get 'recent', on: :collection
  #     end
  #   end

  # Example resource route with concerns:
  #   concern :toggleable do
  #     post 'toggle'
  #   end
  #   resources :posts, concerns: :toggleable
  #   resources :photos, concerns: :toggleable

  # Example resource route within a namespace:
  #   namespace :admin do
  #     # Directs /admin/products/* to Admin::ProductsController
  #     # (app/controllers/admin/products_controller.rb)
  #     resources :products
  #   end
end
