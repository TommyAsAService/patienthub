class Medication < ActiveRecord::Base
  has_many :patient_medications, :foreign_key => :medication_name, :primary_key => :name

  validates :name, :description, presence: true
  validates :name, uniqueness: true
end
