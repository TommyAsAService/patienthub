class Patient < ActiveRecord::Base
  has_many :dosages
  belongs_to :user
  has_many :quiz_feedbacks
  
  accepts_nested_attributes_for :dosages, reject_if: :all_blank, allow_destroy: true
  before_save :ensure_authentication_token!

  validates :name, :age, :patient_number, presence: true
  validates :patient_number, uniqueness: true

  def ensure_authentication_token!
    if self.token_authentication.blank?
      self.token_authentication = generate_authentication_token
    end
  end

  def reset_authentication_token!
    self.token_authentication = generate_authentication_token
    self.save
  end

  private

  def generate_authentication_token
    loop do
      token = Devise.friendly_token
      break token unless Patient.where(token_authentication: token).first
    end
  end
end
