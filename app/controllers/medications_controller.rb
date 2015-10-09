class MedicationsController < ApplicationController
  before_action :authenticate_user!
  before_action :set_medication, only:[:show, :edit, :update]
  def index
    @medications = Medication.all
  end

  def show
  end

  def new
    @medication = Medication.new    
  end

  def create
    @medication = Medication.create(medication_params)
    respond_to do |format|
      if @medication.save
        format.html { redirect_to @medication, notice: 'Medication was successfully created.' }
      else
        format.html { render :new }
      end
    end
  end

  def edit
    
  end

  def update
    respond_to do |format|
      if @medication.update(medication_params)
        format.html { redirect_to @medication, notice: 'Medication was successfully updated'}
      else
        format.html { render :edit }
      end
    end
  end

  private

  def set_medication
    @medication = Medication.find(params[:id])
  end

  def medication_params
    params.require(:medication).permit(:name, :description, :label)
  end
end
