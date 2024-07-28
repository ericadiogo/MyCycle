package com.ericadiogo.mycycle;

import java.io.Serializable;

public class DailyInfo implements Serializable {
    private String date;
    private Boolean perStart,perEnd,intercourse,ovPos,ovNeg,ovPain,acne,headache,migraines;
    private Boolean backaches,breastSens,cramps,musclePain;
    private Boolean pms,weightGain,creamy,dry,foulSmell,green;
    private Boolean wblood,abdominalPain,bloating,constipation,dyarrhea;
    private Boolean anxiety,fatigue,insomnia,stress,tension;

    public DailyInfo(String date, Boolean perStart, Boolean perEnd, Boolean intercourse,
                     Boolean ovPos, Boolean ovNeg, Boolean ovPain, Boolean acne, Boolean headache,
                     Boolean migraines, Boolean backaches, Boolean breastSens, Boolean cramps,
                     Boolean musclePain, Boolean pms, Boolean weightGain, Boolean creamy,
                     Boolean dry, Boolean foulSmell, Boolean green, Boolean wblood,
                     Boolean abdominalPain, Boolean bloating, Boolean constipation,
                     Boolean dyarrhea, Boolean anxiety, Boolean fatigue, Boolean insomnia,
                     Boolean stress, Boolean tension) {
        this.date = date;
        this.perStart = perStart;
        this.perEnd = perEnd;
        this.intercourse = intercourse;
        this.ovPos = ovPos;
        this.ovNeg = ovNeg;
        this.ovPain = ovPain;
        this.acne = acne;
        this.headache = headache;
        this.migraines = migraines;
        this.backaches = backaches;
        this.breastSens = breastSens;
        this.cramps = cramps;
        this.musclePain = musclePain;
        this.pms = pms;
        this.weightGain = weightGain;
        this.creamy = creamy;
        this.dry = dry;
        this.foulSmell = foulSmell;
        this.green = green;
        this.wblood = wblood;
        this.abdominalPain = abdominalPain;
        this.bloating = bloating;
        this.constipation = constipation;
        this.dyarrhea = dyarrhea;
        this.anxiety = anxiety;
        this.fatigue = fatigue;
        this.insomnia = insomnia;
        this.stress = stress;
        this.tension = tension;
    }

    public DailyInfo(){

    }

    public DailyInfo(String date) {
        this.date = date;
    }

    public DailyInfo(String date, Boolean perStart) {
        this.date = date;
        this.perStart = perStart;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Boolean getPerStart() {
        return perStart;
    }

    public void setPerStart(Boolean perStart) {
        this.perStart = perStart;
    }

    public Boolean getPerEnd() {
        return perEnd;
    }

    public void setPerEnd(Boolean perEnd) {
        this.perEnd = perEnd;
    }

    public Boolean getIntercourse() {
        return intercourse;
    }

    public void setIntercourse(Boolean intercourse) {
        this.intercourse = intercourse;
    }

    public Boolean getOvPos() {
        return ovPos;
    }

    public void setOvPos(Boolean ovPos) {
        this.ovPos = ovPos;
    }

    public Boolean getOvNeg() {
        return ovNeg;
    }

    public void setOvNeg(Boolean ovNeg) {
        this.ovNeg = ovNeg;
    }

    public Boolean getOvPain() {
        return ovPain;
    }

    public void setOvPain(Boolean ovPain) {
        this.ovPain = ovPain;
    }

    public Boolean getAcne() {
        return acne;
    }

    public void setAcne(Boolean acne) {
        this.acne = acne;
    }

    public Boolean getHeadache() {
        return headache;
    }

    public void setHeadache(Boolean headache) {
        this.headache = headache;
    }

    public Boolean getMigraines() {
        return migraines;
    }

    public void setMigraines(Boolean migraines) {
        this.migraines = migraines;
    }

    public Boolean getBackaches() {
        return backaches;
    }

    public void setBackaches(Boolean backaches) {
        this.backaches = backaches;
    }


    public Boolean getBreastSens() {
        return breastSens;
    }

    public void setBreastSens(Boolean breastSens) {
        this.breastSens = breastSens;
    }

    public Boolean getCramps() {
        return cramps;
    }

    public void setCramps(Boolean cramps) {
        this.cramps = cramps;
    }

    public Boolean getMusclePain() {
        return musclePain;
    }

    public void setMusclePain(Boolean musclePain) {
        this.musclePain = musclePain;
    }

    public Boolean getPms() {
        return pms;
    }

    public void setPms(Boolean pms) {
        this.pms = pms;
    }

    public Boolean getWeightGain() {
        return weightGain;
    }

    public void setWeightGain(Boolean weightGain) {
        this.weightGain = weightGain;
    }

    public Boolean getCreamy() {
        return creamy;
    }

    public void setCreamy(Boolean creamy) {
        this.creamy = creamy;
    }

    public Boolean getDry() {
        return dry;
    }

    public void setDry(Boolean dry) {
        this.dry = dry;
    }

    public Boolean getFoulSmell() {
        return foulSmell;
    }

    public void setFoulSmell(Boolean foulSmell) {
        this.foulSmell = foulSmell;
    }

    public Boolean getGreen() {
        return green;
    }

    public void setGreen(Boolean green) {
        this.green = green;
    }

    public Boolean getWblood() {
        return wblood;
    }

    public void setWblood(Boolean wblood) {
        this.wblood = wblood;
    }

    public Boolean getAbdominalPain() {
        return abdominalPain;
    }

    public void setAbdominalPain(Boolean abdominalPain) {
        this.abdominalPain = abdominalPain;
    }

    public Boolean getBloating() {
        return bloating;
    }

    public void setBloating(Boolean bloating) {
        this.bloating = bloating;
    }

    public Boolean getConstipation() {
        return constipation;
    }

    public void setConstipation(Boolean constipation) {
        this.constipation = constipation;
    }

    public Boolean getDyarrhea() {
        return dyarrhea;
    }

    public void setDyarrhea(Boolean dyarrhea) {
        this.dyarrhea = dyarrhea;
    }

    public Boolean getAnxiety() {
        return anxiety;
    }

    public void setAnxiety(Boolean anxiety) {
        this.anxiety = anxiety;
    }

    public Boolean getFatigue() {
        return fatigue;
    }

    public void setFatigue(Boolean fatigue) {
        this.fatigue = fatigue;
    }

    public Boolean getInsomnia() {
        return insomnia;
    }

    public void setInsomnia(Boolean insomnia) {
        this.insomnia = insomnia;
    }

    public Boolean getStress() {
        return stress;
    }

    public void setStress(Boolean stress) {
        this.stress = stress;
    }

    public Boolean getTension() {
        return tension;
    }

    public void setTension(Boolean tension) {
        this.tension = tension;
    }
}