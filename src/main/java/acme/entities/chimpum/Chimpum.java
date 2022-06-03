package acme.entities.chimpum;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.entities.item.Item;
import acme.framework.datatypes.Money;
import acme.framework.entities.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Chimpum extends AbstractEntity{
	
	// Serialisation identifier -----------------------------------------------

    protected static final long serialVersionUID = 1L;

    // Attributes -------------------------------------------------------------
    
    @Pattern(regexp = "^[0-9]{3}-[0-9]{3}")
    @NotBlank
    protected String code;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Past
    @NotNull
    protected Date creationMoment;
    
    @NotBlank
    @Length(max = 100)
    protected String title;
    
    @NotBlank
    @Length(max = 255)
    protected String description;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Future
    @NotNull
    protected Date startDate;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Future
    @NotNull
    protected Date finishDate;
    
    @NotNull
    protected Money budget;
    
    @URL
    protected String link;
    
    @Valid
    @OneToOne
    protected Item artefact;
    
    public Long durationPeriodInHours() {
		Long duracion;
		
		final long diferenceInMiliseconds = Math.abs(this.finishDate.getTime() - this.startDate.getTime());
		duracion = TimeUnit.HOURS.convert(diferenceInMiliseconds, TimeUnit.MILLISECONDS);
		return duracion;
	}

	public Long durationPeriodInMinutes() {
		Long duracion;
		
		final long diferenceInMiliseconds = Math.abs(this.finishDate.getTime() - this.startDate.getTime());
		duracion = TimeUnit.MINUTES.convert(diferenceInMiliseconds, TimeUnit.MILLISECONDS);
		return duracion;
	}
    
    

}
