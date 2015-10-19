class DoctorMailer < ActionMailer::Base
  default from: "admin@patienthub.com"

  def summary_email(user, patient)
    @user = user
    @patient = patient
    get_treatment_feedback
    get_feeling_quiz_feedback
    get_most_common_comment
    suggest_action
    mail(to: @user.email, subject: 'PatientHub weekly update regarding ' + patient.name)
  end



  private 
  def get_treatment_feedback
    @total_exercise = 0
    @total_treatments = 0
    @total_misses = 0
    @most_common_reason = nil;
    @dosages = @patient.dosages
    @comment = Hash.new
    @dosages.each do |dosage| 
      feedbacks = dosage.feedbacks.where('created_at >= ?', 1.week.ago)
      @total_treatments += feedbacks.count
      not_taken_feedbacks = feedbacks.where(taken: false)
      @total_misses += not_taken_feedbacks.count
      add_comment(not_taken_feedbacks)
      get_total_exercise(feedbacks.where(taken: true))
    end
  end

  def get_feeling_quiz_feedback
    quiz_feedbacks = @patient.quiz_feedbacks.where('created_at >= ?', 1.week.ago)
    @total_quiz_score = quiz_feedbacks.count * 3
    @total_score = 0
    quiz_feedbacks.each do |quiz_feedback|
      @total_score += convert_string_to_score(quiz_feedback.answer)
    end
  end

  def add_comment(feedbacks)
    feedbacks.each do |feedback|
      if (@comment[feedback.comment] == nil)
        @comment[feedback.comment] = 1
      else
        @comment[feedback.comment] += 1
      end
    end
  end

  def convert_string_to_score(string)
    if string ==  "I slept well" || string == "Yes" || string == "Very motivated"
      return 3
    elsif string == "I woke a few times" || string == "I need some more guidance" || string == "A bit lacking"
      return 2
    elsif string == "I slept very badly" || string == "I am feeling very confused" || string ==  "I am struggling to motivate myself"
      return 1
    else
      return 0
    end
  end

  def suggest_action
    average_score = @total_score.to_f / @total_quiz_score.to_f
    if average_score >= 0.75
      @suggest_action =  "Patient doing well, just keep an eye on future summaries" and return
    elsif average_score >= 0.5
      @suggest_action =  "Ask nurse to follow up with phone call" and return
    else 
      @suggest_action =  "Schedule an appointment with the patient" and return
    end
  end

  def get_most_common_comment
    comment_answer = nil
    comment_value = 0
    @comment.each do |key, value|
      if comment_value < value
        @comment_answer = key
        comment_value = value
      end
    end
  end

  def get_total_exercise(feedbacks)
    feedbacks.each do |feedback|
      if !feedback.comment.nil?
        splited_comment = feedback.comment.split
        @total_exercise += splited_comment[0].to_i
      end
    end
  end

end