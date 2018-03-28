#!/usr/bin/perl 
#use strict; 
#Define all Needed Variables
use List::MoreUtils qw(uniq);
my $path = `pwd`;
chomp ($path);
my $workdir= $path; 
my %map = ();
my @diseasepheno=();
my $database="/home/smailif/Documents/Onto_FollowUp/PhenomeNet/phenotype_annotation.tab";
my $filename= "$path/DiseasePheno_Assoc.lst";
open (my $file, $database);
my $disease = ();
my $genes = ();
my @allgenes =();
open (my $fh, '>>',"$filename")or die "Could not open file '$filename' $!";

while ( my $line =<$file>)
{
	#print ($line);	
	#chomp ($line);
	if ($line =~/HP:(\S+)/)
	{
				
		#print ("Yes \n");		
		my $pheno = "HP_".$1;
		if ($line =~/(OMIM:\S+)/)
		{
			 $disease =$1;
			 my $annotat="$disease annotated_with $pheno";	
			 push @diseasepheno, $annotat;	
			
		}	
		#my $annota = "$gene annotated_with $pheno";
		#push @genepheno, $annota;
	}
}
my @uniq_dis=uniq @diseasepheno;
foreach (@uniq_dis)
{
	print   $fh "$_\n";
}

my $x=@uniq_dis;
print ("$x \n");
