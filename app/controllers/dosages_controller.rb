class DosagesController < ApplicationController
  before_action :authenticate_user!

  def new
    @dosage = Dosage.new
    @dosage.patient_id = params[:patient]    
  end

  def create
    @dosage = Dosage.create(dosage_params)
    respond_to do |format|
      if @dosage.save
        format.html { redirect_to Patient.find(@dosage.patient_id), notice: 'Dosage was successfully created.' }
      else
        format.html { render :new }
      end
    end
  end
  private
  def dosage_params
    params.require(:dosage).permit(:patient_id, :time_taken, :frequency, :start_date, :treatment_name)
  end
end
