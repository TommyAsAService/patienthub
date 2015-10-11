class Feedback < ActiveRecord::Base
  belongs_to :dosage
  
  validates :comment, presence: true
end
