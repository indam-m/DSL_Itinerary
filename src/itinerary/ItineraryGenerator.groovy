/*
 * Source code untuk membangkitkan Itinerary berdasarkan grammar DSL yang dibuat
 * Generator menggunakan bahasa Groovy
 */

package itinerary

import java.nio.file.Path
import java.nio.file.SimpleFileVisitor
import java.nio.file.attribute.BasicFileAttributes

def validInput = true
def id = new Date().time
def input = new String[1024]
def outputFile
def output = ""
def outputFolder = "output/NewItinerary-" + id
def outputFolderCSS = outputFolder + "/assets/css"
def additionBootstrap, additionBasic, additionCustom, additionAwesome
def additionJQuery, additionBootJS, additionJQM, additionCustomJS
def jumDay

def i = 0

new File("input.txt").eachLine { line ->
  input[i] = line
  i++
}

new File("template/header.html").eachLine { line ->
  output += line;
}

// Parsing input
if (input[0]=="itinerary"&&input[i-1]=="end" && i>16){
    
    if(input[1] == "	title" && input[3] == "	endtitle" 
        && input[4] == "	description" && input[6] == "	enddescription"
        && input[7] == "	transport" && input[9] == "	endtransport"
        && input[10] == "	hotel" && input[12] == "	endhotel"
        && input[13] == "	day" && input[i-2] == "	endday"){
        
        output += """<h1 class="page-head-line">"""
        
        String[] judul = input[2].split("\\s+")
        for(int k=0; k<judul.length; k++){
            if(k>0)
                output += """ ${judul[k]}"""
            else
                output += judul[k]
        }
        output += """</h1>
        <h1 class="page-subhead-line">"""
        
        String[] desc = input[5].split("\\s+")
        for(int k=0; k<desc.length; k++){
            if(k>0)
                output += """ ${desc[k]}"""
            else
                output += desc[k]
        }
        output += """</h1>
                    </div>
                </div>
				
                <div class="row">
                        <div class="col-md-8">
        <div class="table-responsive">
            <table class="table table-hover">
        """
        
        jumDay = 0
        
        for(int j=14; j<i-2; j++){
            String[] day = input[j].split("\\s+:\\s+")
            if(day.length == 2){
                String[] day0 = day[0].split("\\s+")
                def day_title = ""
                if(day0.length > 2){
                    for(int k=2; k<day0.length; k++){
                        if(k==2)
                            day_title += day0[k]
                        else
                            day_title += " " + day0[k]
                    }
                } else{
                    validInput = false
                    break
                }
                
                jumDay++
                output += """<thead><tr><th>H-${jumDay}</th><th> ${day_title}</th></tr></thead>"""
                String[] acts = day[1].split("\\s+;\\s+")
                if(acts.length > 0){
                    for(int k=0; k<acts.length; k++){
                        String[] act = acts[k].split("\\s+")
                        if(act.length > 1){
                            if(act[0] == "activity" && act.length>1){
                                def action = ""
                                for(int l=1; l<act.length; l++){
                                    if(l==1)
                                        action += act[l]
                                    else
                                        action += " " + act[l]
                                }
                                output += """
                                            <tr>
                                                <td><span class="label label-warning">
                                                <i class="fa fa-check fa-fw"></i>Aktivitas</span></td>
                                                <td>${action}</td>
                                            </tr>
                                          """
                            } 
                            else if(act[0] == "travel" && act.length>1){
                                def action = ""
                                for(int l=1; l<act.length; l++){
                                    if(l==1)
                                        action += act[l]
                                    else
                                        action += " " + act[l]
                                }
                                output += """
                                            <tr>
                                                <td><span class="label label-warning">
                                                <i class="fa fa-plane fa-fw"></i>Travel</span></td>
                                                <td>${action}</td>
                                            </tr>
                                          """
                            } 
                            else{
                                validInput = false
                                break
                            }
                        }
                        else{
                            validInput = false
                            break
                        }
                    }
                } else{
                    validInput = false
                    break
                }
            } else{
                validInput = false
                break
            }
        }
        
        output += """
        </table>
        """
        String[] trans = input[8].split("\\s+")
        def transport = ""
        for(int k=0; k<trans.length; k++){
            if(k==0)
                transport += trans[k]
            else
                transport += " " + trans[k]
        }
        
        String[] hotel = input[11].split("\\s+")
        def hotels = ""
        for(int k=0; k<hotel.length; k++){
            if(k==0)
                hotels += hotel[k]
            else
                hotels += " " + hotel[k]
        }
        
        output += """
			</div>
                    </div>
				
                            <div class="col-md-4">
                        <div class="panel panel-info">
                            <div class="panel-heading">
                                Informasi Perjalanan
                            </div>

                            <div class="panel-body">
                                <div class="list-group">
                                    <a class="list-group-item">
                                        <i class="fa fa-calendar fa-fw"></i>Periode
                                    <span class="pull-right text-muted small"><em>${jumDay} Hari</em>
                                    </span>
                                    </a>\n\
                                    <a class="list-group-item">
                                        <i class="fa fa-plane fa-fw"></i>Transportasi
                                        <span class="pull-right text-muted small"><em>${transport}</em>
                                        </span>
                                    </a>\n\
                                    <a class="list-group-item">
                                        <i class="fa fa-home fa-fw"></i>Penginapan
                                        <span class="pull-right text-muted small"><em>${hotels}</em>
                                        </span>
                                    </a>
                                </div>
                            </div>
        """
        
    } else{
        validInput = false
    }

} else {
    validInput = false
}

new File("template/footer.html").eachLine { line ->
  output += line;
}

// membuat beberapa file sebagai output
if (validInput){
    new File("template/assets/css/bootstrap.css").eachLine { line ->
        additionBootstrap += line;
      }

    new File("template/assets/css/basic.css").eachLine { line ->
      additionBasic += line;
    }

    new File("template/assets/css/custom.css").eachLine { line ->
      additionCustom += line;
    }
    
    def base = new File(outputFolder);
    base.mkdirs();
    new File(outputFolder + File.separator  +"itinerary+"+id+".html").write(output);

    def base1 = new File(outputFolderCSS);
    base1.mkdirs();

    new File(outputFolderCSS + File.separator  +"bootstrap.css").write(additionBootstrap);
    new File(outputFolderCSS + File.separator  +"basic.css").write(additionBasic);
    new File(outputFolderCSS + File.separator  +"custom.css").write(additionCustom);

} else { // tidak mengeluarkan output jika format tidak valid
    println "format tidak valid"
}