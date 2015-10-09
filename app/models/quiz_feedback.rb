class QuizFeedback < ActiveRecord::Base
  belongs_to :patient

  validates :question, :answer, presence: true
end
