package br.com.ande.model;

import java.util.Date;

import br.com.ande.Ande;
import br.com.ande.R;
import br.com.ande.util.DateUtils;

/**
 * © Copyright 2017 Ande.
 * Autor : Paulo Sales - dev@paulovns.com.br
 * Empresa : Ande app.
 */

public class History {

    /**
     * show some text like "segunda-feira, 20 de abr 2017"
     */
    private String  descriptionHistory;

    /**
     * show some text like "passo(s) até o momento" or "passo(s) dados"
     */
    private String  descriptionSteps;

    /**
     * Number of steps
     */
    private int     steps;

    /**
     * Verify if is current day
     */
    private boolean isCurrentDay;

    //TODO:: pass param type HistoryDAO
    public History() {
        String description = Ande.getContext().getString(R.string.history_description);

        this.descriptionHistory = String.format(description, new Date());
        this.setDecriptionOfSteps(new Date());
        this.steps              = steps;

        this.descriptionSteps = String.format(this.descriptionSteps, this.steps);
    }

    private void setDecriptionOfSteps(Date date){

        if(DateUtils.isCurrentDay(date))
            this.descriptionSteps = Ande.getContext().getString(R.string.history_description_steps);
        else
            this.descriptionSteps   = Ande.getContext().getString(R.string.history_description_steps_finished);

    }

}
