class Treatment < ActiveRecord::Base
  has_many :dosages, :foreign_key => :treatment_name, :primary_key => :name

  validates :name, presence: true, uniqueness: true
end
