module Api
  module V1
    class PatientsController < ApiController
      def get_patient
        render :json => @patient.to_json(except:["token_authentication"])
      end

      def get_dosages
        render :json => @patient.dosages.to_json(:include => :treatment )
      end
    end
  end
end