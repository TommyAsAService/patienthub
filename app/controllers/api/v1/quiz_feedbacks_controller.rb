module Api
  module V1
    class QuizFeedbacksController < ApiController
      def new
      end

      def create
        @quiz_feedback = QuizFeedback.create(quiz_feedback_params)
        @quiz_feedback.patient_id = @patient.id
        if @quiz_feedback.save
          render :status=>200, :json=>{:message=>'Quiz feedback is successfully save'}
        else
          render :status=>400, :json=>{:message=>'The request must contain valid fields.'}
        end
      end

      private

      def quiz_feedback_params
        params.require(:quiz_feedback).permit(:question, :answer, :patient_id)
      end
    end
  end
end