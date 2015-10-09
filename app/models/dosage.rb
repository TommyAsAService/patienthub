class Dosage < ActiveRecord::Base
  belongs_to :patient
  belongs_to :treatment, :foreign_key => :treatment_name, :primary_key => :name
  has_one :feedback
  
  validates :frequency, :numericality => { :greater_than => 0 }
  validates :patient_id, :time_taken, :frequency, :start_date, :treatment_name, :quantity, :unit, presence: true

  def get_frequency_string
    if self.frequency == 1
      return "Everyday"
    else
      return "Every " + self.frequency.to_s + " days"
    end
  end

  def get_quality_unit
    if (self.quantity.modulo(1) == 0.0)
      self.quantity.to_i.to_s + " " + self.unit.to_s
    else
      self.quantity.to_s + " " + self.unit.to_s
    end
  end
end
