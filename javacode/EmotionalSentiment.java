/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package sentimentanalysis;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;

/**
 *
 * @author ajay
 */
public class EmotionalSentiment {

    String[] negativeList = { "%-(",")-:","):",")o:","38*","8-0","8/","8\\","8c",":#",":'(",":'-(",":(",":*(",":,(",
                              ":-&",":-(",":-(o)",":-/",":-S",":-\\",":-|",":/",":E",":F",":O",":S",":[",":[",":\\",
                              ":_(",":o(",":s",":|",":�(","</3-1","<o<","=(","=[",">/",">:(",">:L",">:O",">[",">\\",
                              ">o>","B(","Bc","D:","X(","X(","X-(","XO","XP","^o)","xP","|8C","|8c" };

    String[] positiveList = { "%-)","(-:","(:","(^ ^)","(^-^)","(^.^)","(^_^)","(o:","*\\o/*","--^--@","0:)","8)",":)",
                              ":-)",":-*",":-*",":-D",":-P",":-}",":3",":9",":D",":P",":P",":X",":]",":b)",":o)",":p",
                              ":�",";^)","<3","=)","=]",">:)",">:D",">=D","@}->--","XD","XD","XP","^_^","x3?","xD","|D","}:)"};

    public static String[] negativeLookTable = { "abandon","abuse","abusi","ache","aching","advers","afraid","aggravat","aggress","agitat",
"agoniz","agony","alarm","alone","anger","angr","anguish","annoy","antagoni","anxi",
"apath","appall","apprehens","argh","argu","arrogan","asham","assault","asshole","attack",
"aversi","awful","awkward","bad","bashful","bastard","beaten","bitch","bitter","blam",
"bore","boring","bother","broke","brutal","bum","bumhole","burden","careless","cheat",
"complain","confront","confus","contempt","contradic","crap","crappy","craz","cried","cries",
"critical","critici","crude","cruel","crushed","cry","crye","crying","cunt","cynic",
"damag","damn","danger","daze","decay","defeat","defect","defenc","degrad","depress",
"depriv","despair","desperat","despis","destroy","destruct","devastat","devil","difficult","disappoint",
"disaster","discomfort","discourag","disgust","dishearten","disillusion","dislike","disliked","dislikes","disliking",
"dismay","dissatisf","distract","distraught","distress","distrust","disturb","doom","dork","doubt",
"dread","dull","dumb","dump","dwell","egotis","embarrass","emotional","empt","enemie",
"enemy","enrag","envie","envious","envy","evil","exhaust","fail","fake","fatal",
"fatigu","fault","fear","feared","fearful","fearing","fears","feroc","feud","fiery",
"fight","fired","flame","flamed","flunk","foe","fool","forbid","fought","frantic",
"freak","fright","frustrat","fuck","fucked","fucker","fuckin","fucks","fud","fume",
"fuming","furious","fury","gay","geek","gloom","goddam","gossip","grave","greed",
"grief","griev","grim","gross","grouch","grr","guilt","h8","hack","harass",
"harm","harmed","harmful","harming","harms","hate","hated","hateful","hater","hates",
"hating","hatred","heartbreak","heartbroke","heartless","hectic","hell","hellish","helpless","hesita",
"homesick","hopeless","horr","hostil","humiliat","hungr","hurt","idiot","ignor","immoral",
"impatien","impersonal","impolite","inadequa","indecis","ineffect","inferior","inhib","insecur","insincer",
"insult","interrup","intimidat","irrational","irrita","isolat","jaded","jealous","jerk","jerked",
"jerks","killed","killer","killing","lame","lazie","lazy","liabilit","liar","lied",
"lies","lone","longing","lose","loser","loses","losing","loss","lost","lous",
"low","luckless","ludicrous","lying","mad","maddening","madder","maddest","maniac","masochis",
"melanchol","mess","messy","miser","miss","missed","misses","missing","mistak","mock",
"mocked","mocker","mocking","mocks","molest","mooch","moodi","moody","moron","mourn",
"murder","nag","nast","needy","neglect","nerd","nervous","neurotic","numb","nutter",
"obnoxious","obsess","offence","offend","offens","outrag","overwhelm","pain","pained","painf",
"paining","pains","panic","paranoi","pathetic","peculiar","perver","pessimis","petrif","pettie",
"petty","phobi","piss","piti","pity","poison","poor","prejudic","pressur","prick",
"problem","protest","protested","protesting","puk","punish","rage","raging","rancid","rape",
"raping","rapist","rat","rebel","reek","regret","reject","reluctan","remorse","repress",
"resent","resign","restless","revenge","ridicul","rigid","risk","rotten","rude","ruin",
"sad","sadde","sadly","sadness","sarcas","savage","scare","scariest","scaring","scary",
"sceptic","scream","screw","scum","scummy","selfish","serious","seriously","seriousness","severe",
"shake","shaki","shaky","shame","shit","shock","shook","shy","sicken","sin",
"sinister","sins","skeptic","slut","smother","smug","sneak","snob","sob","sobbed",
"sobbing","sobs","solemn","sorrow","sorry","spam","spite","squirm","stammer","stank",
"startl","steal","stench","stink","strain","strange","stress","struggl","stubborn","stunk",
"stunned","stuns","stupid","stutter","submissive","suck","sucked","sucker","sucks","sucky",
"suffer","suffered","sufferer","suffering","suffers","suk","suspicio","tantrum","tears","teas",
"temper","tempers","tense","tensing","tension","terribl","terrified","terrifies","terrify","terrifying",
"terror","thief","thieve","thirsty","threat","ticked","timid","tortur","tough","traged",
"tragic","trauma","trembl","trick","trite","trivi","troubl","turmoil","ugh","ugl",
"unattractive","uncertain","uncomfortabl","uncontrol","uneas","unfortunate","unfriendly","ungrateful","unhapp","unimportant",
"unimpress","unkind","unlov","unpleasant","unprotected","unsavo","unsuccessful","unsure","unwelcom","upset",
"uptight","useless","vain","vanity","vicious","victim","vile","villain","violat","violent",
"vulnerab","vulture","war","warfare","warred","warring","wars","weak","weapon","weep",
"weird","wept","whine","whining","whore","wickedn","wimp","witch","woe","worr",
"worse","worst","worthless","wrong","wtf","yearn",
"aren't","arent","can't","cannot","cant","couldn't","couldnt","don't","dont","isn't","isnt",
"never","not","won't","wont","wouldn't","wouldnt"};
    
    public static String[] positiveLookTable = { "admir","ador","adventur","affection","agreeab","alol","amaz","amor","amus","aok",
"appreciat","attachment","attract","award","awesome","baby","beaut","beloved","best","bff",
"bg","bless","bonus","brave","brillian","calm","care","cared","carefree","cares",
"caring","charm","cheer","cherish","chuckl","clever","comed","comfort","compassion","compliment",
"confidence","confident","confidently","considerate","contented","contentment","cool","coolest","courag","cute",
"cutie","daring","darlin","dear","delectabl","delicious","deligh","determina","determined","devot",
"digni","divin","dynam","eager","ease","easie","easily","easiness","easing","easy",
"ecsta","elegan","encourag","energ","engag","enjoy","enthus","excel","excit","fab",
"fabulous","faith","fantastic","favor","favour","fearless","festiv","fiesta","fine","flawless",
"flexib","flirt","fond","fondly","fondness","forgave","forgiv","fun","funn","genero",
"gentle","gentler","gentlest","gently","giggl","glad","gladly","glamor","glamour","glori",
"glory","gmbo","good","goodness","gorgeous","grace","graced","graceful","graces","graci",
"grand","grande","gratef","grati","great","grin","grinn","grins","ha","haha",
"handsom","happi","happy","harmless","harmon","heartfelt","heartwarm","heaven","hehe","hero",
"hey","hilarious","hoho","homie","honest","honor","honour","hope","hoped","hopeful",
"hopefully","hopefulness","hopes","hoping","hug","hugg","hugs","humor","humour","hurra",
"importan","impress","improve","improving","incentive","innocen","inspir","intell","interest","invigor",
"joke","joking","joll","joy","keen","kidding","kind","kindly","kindn","kiss",
"laidback","laugh","like","likeab","liked","likes","liking","livel","lmao","lol",
"lolol","love","loved","lovely","lover","loves","lovin","loving","loyal","luck",
"lucked","lucki","lucks","lucky","luv","madly","magnific","mate","merit","merr",
"muah","neat","nice","nurtur","ok","okay","okays","oks","omfg","omg",
"openminded","openness","opportun","optimal","optimi","original","outgoing","painl","palatabl","paradise",
"partie","party","passion","peace","perfect","play","played","playful","playing","plays",
"pleasant","please","pleasing","pleasur","popular","positiv","prais","precious","prettie","pretty",
"pride","privileg","prize","profit","promis","proud","radian","readiness","ready","reassur",
"relax","relief","reliev","resolv","respect","revigor","reward","rich","rock","rofl",
"romanc","romantic","safe","satisf","save","scrumptious","secur","sentimental","sexy","share",
"shared","shares","sharing","silli","silly","sincer","smart","smil","sociab","soulmate",
"special","splend","strength","strong","succeed","success","sunnier","sunniest","sunny","sunshin",
"sup","super","superior","support","supported","supporter","supporting","supportive","supports","suprem",
"sure","surpris","sweet","sweetheart","sweetie","sweetly","sweetness","sweets","talent","tehe",
"tender","terrific","thank","thanked","thankf","thanks","thanx","thnx","thoughtful","thrill",
"toleran","tranquil","treasur","treat","triumph","true","trueness","truer","truest","truly",
"trust","truth","useful","valuabl","value","valued","values","valuing","vigor","vigour",
"virtue","virtuo","vital","warm","wealth","welcom","well","wicked","win","winn",
"wins","wisdom","wise","won","wonderf","worship","worthwhile","wow","x","xox",
"xx","yay","yays","great"};



    public static HashMap<String, String> positiveHashMap;
    public static HashMap<String, String> negativeHashMap;
//    public static HashMap<String, String> positiveLookUp;
//    public static HashMap<String, String> negativeLookUp;

    public EmotionalSentiment () {
        positiveHashMap = new HashMap();
        negativeHashMap = new HashMap();

        for ( int i = 0 ; i < positiveList.length ; i++ ) {
            positiveHashMap.put(positiveList[i],"*");
        }

        for ( int i = 0 ; i < negativeList.length ; i++ ) {
            negativeHashMap.put(negativeList[i],"*");
        }

//        positiveLookUp = new HashMap<String, String>();
//        negativeLookUp = new HashMap<String, String>();
//
//        for ( int i = 0 ; i < positiveLookTable.length ; i++ ) {
//            positiveLookUp.put(positiveLookTable[i], "*");
//        }
//
//        for ( int i = 0 ; i < negativeLookTable.length ; i++ ) {
//            negativeLookUp.put(negativeLookTable[i], "*");
//        }

    }

    public static boolean isPositiveSentiment ( String str ) {
        if ( positiveHashMap.containsKey(str) ) return true;
	else return false;
    }

    public static boolean isNegativeSentiment ( String str ) {
        if ( negativeHashMap.containsKey(str) ) return true;
	else return false;
    }

    public Double checkEmotionalSentiment ( String str ) {
//        System.out.println("hhh : " + str);
        if ( isPositiveSentiment(str) ) return 1.0;
        else if ( isNegativeSentiment(str) ) return -1.0;
        return 0.0;
    }

    public Double checkEmotionalSentimentLookUpTable ( String str ) {
//        if ( positiveLookUp.containsKey(str) ) return 1.0;
//        else if ( negativeLookUp.containsKey(str) ) return -1.0;
//        else return 0.0;
        for ( String temp : positiveLookTable  ) {            
            if ( str.startsWith(temp) ) {
                return 1.0;
            }
        }

        for ( String temp : negativeLookTable ) {
            if ( str.startsWith(temp) ) {
                return -1.0;
            }
        }

        return 0.0;
    }

    public static void main ( String[] args ) throws FileNotFoundException, IOException {
       
    }
}
