class User < ActiveRecord::Base
  # Include default devise modules.

  before_validation :set_provider
  before_validation :set_uid
  
  devise :database_authenticatable, :registerable,
          :recoverable, :rememberable, :trackable, :validatable,
          :omniauthable
  include DeviseTokenAuth::Concerns::User

  
  private
  def set_provider
    self[:provider] = "email" if self[:provider].blank?
  end

        
  def set_uid
    self[:uid] = self[:email] if self[:uid].blank? && self[:email].present?
  end
end
