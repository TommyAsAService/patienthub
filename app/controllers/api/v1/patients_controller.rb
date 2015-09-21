module Api
  module V1
    class PatientsController < ApiController
      def get_patient
        render :json => @patient.to_json(except:["token_authentication"])
      end
    end
  end
end