class TreatmentsController < ApplicationController
  before_action :authenticate_user!
  before_action :set_treatment, only:[:show, :edit, :update]
  def index
    @treatments = Treatment.all
  end

  def show
  end

  def new
    @treatment = Treatment.new    
  end

  def create
    @treatment = Treatment.create(treatment_params)
    respond_to do |format|
      if @treatment.save
        format.html { redirect_to @treatment, notice: 'Treatment was successfully created.' }
      else
        format.html { render :new }
      end
    end
  end

  def edit
    
  end

  def update
    respond_to do |format|
      if @treatment.update(treatment_params)
        format.html { redirect_to @treatment, notice: 'Treatment was successfully updated'}
      else
        format.html { render :edit }
      end
    end
  end

  private

  def set_treatment
    @treatment = Treatment.find(params[:id])
  end

  def treatment_params
    params.require(:treatment).permit(:name, :quantity, :treatment_type, :unit)
  end
end
