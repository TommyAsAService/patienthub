module Api
  module V1
    class FeedbacksController < ApiController
      def new
      end

      def create
        @feedback = Feedback.create(feedback_params)
        if @feedback.save
          render :status=>200, :json=>{:message=>'Medication feedback is successfully save'}
        else
          render :status=>400, :json=>{:message=>'The request must contain valid fields.'}
        end
      end

      private

      def feedback_params
        params.require(:feedback).permit(:dosage_id, :taken, :comment)
      end
    end
  end
end