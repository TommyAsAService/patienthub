class Treatment < ActiveRecord::Base
  has_many :dosages, :foreign_key => :treatment_name, :primary_key => :name

  validates :name, :treatment_type, presence: true
  validates :name, uniqueness: true
end
