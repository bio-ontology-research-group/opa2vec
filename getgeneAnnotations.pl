#!/usr/bin/perl 
#use strict; 
#Define all Needed Variables
use List::MoreUtils qw(uniq);
my $path = `pwd`;
chomp ($path);
my $workdir= $path; 
my %map = ();
my @genepheno=();
my $database="/home/smailif/Documents/Onto_FollowUp/PhenomeNet/MGI_PhenoGenoMP.rpt";
my $filename= "$path/GenePheno_Assoc.lst";
open (my $file, $database);
my $gene = ();
my $genes = ();
my @allgenes =();
open (my $fh, '>>',"$filename")or die "Could not open file '$filename' $!";

while ( my $line =<$file>)
{
	#print ($line);	
	#chomp ($line);
	if ($line =~/MP:(\S+)/)
	{
				
		#print ("Yes \n");		
		my $pheno = "MP_".$1;
		if ($line =~/(MGI:\S+)/)
		{
			 $genes =$1;
			 if ($genes =~/,/)
			{
				@allgenes = split (/,/, $genes);	 
				 foreach (@allgenes)
					{
						my $gene =$_;					
						my $annota = "$gene annotated_with $pheno";
						push @genepheno, $annota;
					}
			}
			else
			{
				my $annotat="$genes annotated_with $pheno";	
				push @genepheno, $annotat;	
			}	
		}	
		#my $annota = "$gene annotated_with $pheno";
		#push @genepheno, $annota;
	}
}
my @uniq_gene=uniq @genepheno;
foreach (@uniq_gene)
{
	print  $fh "$_\n";
}

