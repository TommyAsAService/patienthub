class Feedback < ActiveRecord::Base
  belongs_to :dosage
  
  validates :taken, :comment, presence: true
end
