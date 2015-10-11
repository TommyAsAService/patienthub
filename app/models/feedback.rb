class Feedback < ActiveRecord::Base
  belongs_to :dosage
  
  validates :comment, presence: true
  validates :taken, :inclusion => { :in => [true, false] }
end
