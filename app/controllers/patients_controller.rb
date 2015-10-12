class PatientsController < ApplicationController
  before_action :authenticate_user!
  before_action :set_patient, only:[:show, :edit, :update, :generate_qr]

  autocomplete :treatment, :name

  def index
    @patients = current_user.patients
  end

  def show
  end

  def new
    @patient = Patient.new :user_id => current_user.id
  end

  def create
    @patient = Patient.create(patient_params)
    @patient.user_id = current_user.id
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

  def generate_qr
    qrcode = RQRCode::QRCode.new(@patient.token_authentication)
    image = qrcode.as_png
    name = Time.now.strftime('%Y%m%d%H%M%S%L') + ".png"
    path = Rails.root + "tmp/" + name
    image.save(path)
    send_file path,:type=>"application/png", :x_sendfile=>true
  end

  def mail_to_doctor
    set_patient
    DoctorMailer.summary_email(current_user, @patient).deliver_now
    head :no_content
    # render :text=> "Successfully sent email. Please check your mail box.", :status => 200, :content_type => 'text/html'
  end

  private

  def set_patient
    @patient = Patient.find(params[:id])
  end

  def patient_params
    params.require(:patient).permit(
      :name, :address, :contact_number, :age, :patient_number,
      dosages_attributes: [:id, :_destroy, :time_taken, :frequency, :start_date, :treatment_name, :patient_id, :quantity, :unit] )
  end
end
