class Dosage < ActiveRecord::Base
  belongs_to :patient
  belongs_to :treatment, :foreign_key => :treatment_name, :primary_key => :name

  validates :frequency, :numericality => { :greater_than => 0 }
end
