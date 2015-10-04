class User < ActiveRecord::Base
  # Include default devise modules.
  has_many :patients
  devise :database_authenticatable, :registerable,
          :recoverable, :rememberable, :trackable, :validatable,
          :omniauthable
  validates :name, :contact, :working_address, :email, :password, :password_confirmation, :presence => true
end
