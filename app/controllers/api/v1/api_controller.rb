module Api
  module V1
    class ApiController < ApplicationController
      before_action :authenticate_with_token
      private
        def authenticate_with_token
          authenticate_or_request_with_http_token do |token, options|
            @patient = Patient.find_by(token_authentication: token)
          end
        end
    end
  end
end