class PatientSignUp
  include ActiveModel::Model

  attr_accessor :organisation_name, :first_name, :surname, :email, :password, :password_confirmation
  attr_reader :medication, :patient