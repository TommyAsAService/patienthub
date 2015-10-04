class PatientMedication < ActiveRecord::Base
  belongs_to :medication, :foreign_key => :medication_name, :primary_key => :name
  belongs_to :patient

  validates :time_take_per_day, :amount_given, :numericality => { :greater_than => 0 }
  validates :start_time, :time_take_per_day, :amount_given, :medication_name, :presence => true
end
