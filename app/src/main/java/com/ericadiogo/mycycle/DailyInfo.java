package com.ericadiogo.mycycle;

import java.io.Serializable;

public class DailyInfo implements Serializable {

   private  SelDate selDate;

   public class SelDate implements Serializable{
       private String perStart, perEnd;
       private int flowRating,intRating;
       private Boolean ovPos,ovNeg,ovPain,acne,dizziness,headache,migraine,backaches,bodyaches;
       private Boolean breastsens,chills,cramps,hotflashes,illness,influenza,itchiness,musclepain;
       private Boolean nightsweats,pms,reashes,shoulderaches,tenderbreasts,weightgain,cervfirm;
       private Boolean cervopen,irritation,spotting,cottagecheese,creamy,dry,eggwhite,foulsmell;
       private Boolean green,sticky,watery,wblood,abdominalpain,bloating,constipation,cravings;
       private Boolean dyarrhea,dyspepsia,gas,hunger,nausea,anxiety,confusion,fatigue,insomnia,moodiness;
       private Boolean stress,tension;

       public SelDate(String perStart, String perEnd, int flowRating, int intRating, Boolean ovPos,
                      Boolean ovNeg, Boolean ovPain, Boolean acne, Boolean dizziness, Boolean headache,
                      Boolean migraine, Boolean backaches, Boolean bodyaches, Boolean breastsens,
                      Boolean chills, Boolean cramps, Boolean hotflashes, Boolean illness,
                      Boolean influenza, Boolean itchiness, Boolean musclepain, Boolean nightsweats,
                      Boolean pms, Boolean reashes, Boolean shoulderaches, Boolean tenderbreasts,
                      Boolean weightgain, Boolean cervfirm, Boolean cervopen, Boolean irritation,
                      Boolean spotting, Boolean cottagecheese, Boolean creamy, Boolean dry,
                      Boolean eggwhite, Boolean foulsmell, Boolean green, Boolean sticky,
                      Boolean watery, Boolean wblood, Boolean abdominalpain, Boolean bloating,
                      Boolean constipation, Boolean cravings, Boolean dyarrhea, Boolean dyspepsia,
                      Boolean gas, Boolean hunger, Boolean nausea, Boolean anxiety,
                      Boolean confusion, Boolean fatigue, Boolean insomnia, Boolean moodiness,
                      Boolean stress, Boolean tension) {
           this.perStart = perStart;
           this.perEnd = perEnd;
           this.flowRating = flowRating;
           this.intRating = intRating;
           this.ovPos = ovPos;
           this.ovNeg = ovNeg;
           this.ovPain = ovPain;
           this.acne = acne;
           this.dizziness = dizziness;
           this.headache = headache;
           this.migraine = migraine;
           this.backaches = backaches;
           this.bodyaches = bodyaches;
           this.breastsens = breastsens;
           this.chills = chills;
           this.cramps = cramps;
           this.hotflashes = hotflashes;
           this.illness = illness;
           this.influenza = influenza;
           this.itchiness = itchiness;
           this.musclepain = musclepain;
           this.nightsweats = nightsweats;
           this.pms = pms;
           this.reashes = reashes;
           this.shoulderaches = shoulderaches;
           this.tenderbreasts = tenderbreasts;
           this.weightgain = weightgain;
           this.cervfirm = cervfirm;
           this.cervopen = cervopen;
           this.irritation = irritation;
           this.spotting = spotting;
           this.cottagecheese = cottagecheese;
           this.creamy = creamy;
           this.dry = dry;
           this.eggwhite = eggwhite;
           this.foulsmell = foulsmell;
           this.green = green;
           this.sticky = sticky;
           this.watery = watery;
           this.wblood = wblood;
           this.abdominalpain = abdominalpain;
           this.bloating = bloating;
           this.constipation = constipation;
           this.cravings = cravings;
           this.dyarrhea = dyarrhea;
           this.dyspepsia = dyspepsia;
           this.gas = gas;
           this.hunger = hunger;
           this.nausea = nausea;
           this.anxiety = anxiety;
           this.confusion = confusion;
           this.fatigue = fatigue;
           this.insomnia = insomnia;
           this.moodiness = moodiness;
           this.stress = stress;
           this.tension = tension;
       }

       public SelDate(){

       }

       public String getPerStart() {
           return perStart;
       }

       public void setPerStart(String perStart) {
           this.perStart = perStart;
       }

       public String getPerEnd() {
           return perEnd;
       }

       public void setPerEnd(String perEnd) {
           this.perEnd = perEnd;
       }

       public int getFlowRating() {
           return flowRating;
       }

       public void setFlowRating(int flowRating) {
           this.flowRating = flowRating;
       }

       public int getIntRating() {
           return intRating;
       }

       public void setIntRating(int intRating) {
           this.intRating = intRating;
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

       public Boolean getDizziness() {
           return dizziness;
       }

       public void setDizziness(Boolean dizziness) {
           this.dizziness = dizziness;
       }

       public Boolean getHeadache() {
           return headache;
       }

       public void setHeadache(Boolean headache) {
           this.headache = headache;
       }

       public Boolean getMigraine() {
           return migraine;
       }

       public void setMigraine(Boolean migraine) {
           this.migraine = migraine;
       }

       public Boolean getBackaches() {
           return backaches;
       }

       public void setBackaches(Boolean backaches) {
           this.backaches = backaches;
       }

       public Boolean getBodyaches() {
           return bodyaches;
       }

       public void setBodyaches(Boolean bodyaches) {
           this.bodyaches = bodyaches;
       }

       public Boolean getBreastsens() {
           return breastsens;
       }

       public void setBreastsens(Boolean breastsens) {
           this.breastsens = breastsens;
       }

       public Boolean getChills() {
           return chills;
       }

       public void setChills(Boolean chills) {
           this.chills = chills;
       }

       public Boolean getCramps() {
           return cramps;
       }

       public void setCramps(Boolean cramps) {
           this.cramps = cramps;
       }

       public Boolean getHotflashes() {
           return hotflashes;
       }

       public void setHotflashes(Boolean hotflashes) {
           this.hotflashes = hotflashes;
       }

       public Boolean getIllness() {
           return illness;
       }

       public void setIllness(Boolean illness) {
           this.illness = illness;
       }

       public Boolean getInfluenza() {
           return influenza;
       }

       public void setInfluenza(Boolean influenza) {
           this.influenza = influenza;
       }

       public Boolean getItchiness() {
           return itchiness;
       }

       public void setItchiness(Boolean itchiness) {
           this.itchiness = itchiness;
       }

       public Boolean getMusclepain() {
           return musclepain;
       }

       public void setMusclepain(Boolean musclepain) {
           this.musclepain = musclepain;
       }

       public Boolean getNightsweats() {
           return nightsweats;
       }

       public void setNightsweats(Boolean nightsweats) {
           this.nightsweats = nightsweats;
       }

       public Boolean getPms() {
           return pms;
       }

       public void setPms(Boolean pms) {
           this.pms = pms;
       }

       public Boolean getReashes() {
           return reashes;
       }

       public void setReashes(Boolean reashes) {
           this.reashes = reashes;
       }

       public Boolean getShoulderaches() {
           return shoulderaches;
       }

       public void setShoulderaches(Boolean shoulderaches) {
           this.shoulderaches = shoulderaches;
       }

       public Boolean getTenderbreasts() {
           return tenderbreasts;
       }

       public void setTenderbreasts(Boolean tenderbreasts) {
           this.tenderbreasts = tenderbreasts;
       }

       public Boolean getWeightgain() {
           return weightgain;
       }

       public void setWeightgain(Boolean weightgain) {
           this.weightgain = weightgain;
       }

       public Boolean getCervfirm() {
           return cervfirm;
       }

       public void setCervfirm(Boolean cervfirm) {
           this.cervfirm = cervfirm;
       }

       public Boolean getCervopen() {
           return cervopen;
       }

       public void setCervopen(Boolean cervopen) {
           this.cervopen = cervopen;
       }

       public Boolean getIrritation() {
           return irritation;
       }

       public void setIrritation(Boolean irritation) {
           this.irritation = irritation;
       }

       public Boolean getSpotting() {
           return spotting;
       }

       public void setSpotting(Boolean spotting) {
           this.spotting = spotting;
       }

       public Boolean getCottagecheese() {
           return cottagecheese;
       }

       public void setCottagecheese(Boolean cottagecheese) {
           this.cottagecheese = cottagecheese;
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

       public Boolean getEggwhite() {
           return eggwhite;
       }

       public void setEggwhite(Boolean eggwhite) {
           this.eggwhite = eggwhite;
       }

       public Boolean getFoulsmell() {
           return foulsmell;
       }

       public void setFoulsmell(Boolean foulsmell) {
           this.foulsmell = foulsmell;
       }

       public Boolean getGreen() {
           return green;
       }

       public void setGreen(Boolean green) {
           this.green = green;
       }

       public Boolean getSticky() {
           return sticky;
       }

       public void setSticky(Boolean sticky) {
           this.sticky = sticky;
       }

       public Boolean getWatery() {
           return watery;
       }

       public void setWatery(Boolean watery) {
           this.watery = watery;
       }

       public Boolean getWblood() {
           return wblood;
       }

       public void setWblood(Boolean wblood) {
           this.wblood = wblood;
       }

       public Boolean getAbdominalpain() {
           return abdominalpain;
       }

       public void setAbdominalpain(Boolean abdominalpain) {
           this.abdominalpain = abdominalpain;
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

       public Boolean getCravings() {
           return cravings;
       }

       public void setCravings(Boolean cravings) {
           this.cravings = cravings;
       }

       public Boolean getDyarrhea() {
           return dyarrhea;
       }

       public void setDyarrhea(Boolean dyarrhea) {
           this.dyarrhea = dyarrhea;
       }

       public Boolean getDyspepsia() {
           return dyspepsia;
       }

       public void setDyspepsia(Boolean dyspepsia) {
           this.dyspepsia = dyspepsia;
       }

       public Boolean getGas() {
           return gas;
       }

       public void setGas(Boolean gas) {
           this.gas = gas;
       }

       public Boolean getHunger() {
           return hunger;
       }

       public void setHunger(Boolean hunger) {
           this.hunger = hunger;
       }

       public Boolean getNausea() {
           return nausea;
       }

       public void setNausea(Boolean nausea) {
           this.nausea = nausea;
       }

       public Boolean getAnxiety() {
           return anxiety;
       }

       public void setAnxiety(Boolean anxiety) {
           this.anxiety = anxiety;
       }

       public Boolean getConfusion() {
           return confusion;
       }

       public void setConfusion(Boolean confusion) {
           this.confusion = confusion;
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

       public Boolean getMoodiness() {
           return moodiness;
       }

       public void setMoodiness(Boolean moodiness) {
           this.moodiness = moodiness;
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

    public DailyInfo(SelDate selDate) {
        this.selDate = selDate;
    }

    public DailyInfo() {

    }

    public SelDate getSelDate() {
        return selDate;
    }

    public void setSelDate(SelDate selDate) {
        this.selDate = selDate;
    }
}


