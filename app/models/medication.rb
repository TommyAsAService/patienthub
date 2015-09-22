class Medication < ActiveRecord::Base
  has_many :patient_medications, foreign_key: :name, primary_key: :name

  validates :name, :description, presence: true
  validates :name, uniqueness: true

  def medication_display
    "#{self.id}: #{self.name}"
  end
end
