class PatientsController < ApplicationController
  before_action :authenticate_user!
  before_action :set_patient, only:[:show, :edit, :update]
  def index
    @patients = Patient.all
  end

  def show
  end

  def new
    @patient = Patient.new    
  end

  def create
    @patient = Patient.create(patient_params)
    respond_to do |format|
      if @patient.save
        format.html { redirect_to @patient, notice: 'Patient was successfully created.' }
      else
        format.html { render :new }
      end
    end
  end

  def edit
    
  end

  def update
    respond_to do |format|
      if @patient.update(patient_params)
        format.html { redirect_to @patient, notice: 'Patient was successfully updated'}
      else
        format.html { render :edit }
      end
    end
  end

  private

  def set_patient
    @patient = Patient.find(params[:id])
  end

  def patient_params
    params.require(:patient).permit(:name, :address, :contact_number, :age, :patient_number)
  end
end
