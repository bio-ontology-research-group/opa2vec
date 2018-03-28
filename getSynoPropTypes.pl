#!/usr/bin/perl 
#use strict; 
#Define all Needed Variables
use List::MoreUtils qw(uniq);
my $path = `pwd`;
chomp ($path);
my $workdir= $path; 
my %map = ();
my @genepheno=();
my $database="$path/allproperties.lst";
my $filename= "$path/Alldiseases.lst";
open (my $file, $database);
my $gene = ();
my $genes = ();
my @allgenes =();
open (my $fh, '>>',"$filename")or die "Could not open file '$filename' $!";

while ( my $line =<$file>)
{
	
	#if ($line =~ /Synonym/)
	#{
		
		push @allgenes, $line;	
	#}
}
my @uniq_genes=uniq @allgenes;
foreach (@uniq_genes)
{
	print   "$_\n";
}


