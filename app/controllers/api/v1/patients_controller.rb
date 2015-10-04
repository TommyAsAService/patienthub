module Api
  module V1
    class PatientsController < ApiController
      def get_patient
        render :json => @patient.to_json(except:["token_authentication"])
      end

      def get_medication
        render :json => @patient.patient_medications.to_json(:include => :medication)
      end
    end
  end
end