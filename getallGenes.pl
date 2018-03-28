#!/usr/bin/perl 
#use strict; 
#Define all Needed Variables
use List::MoreUtils qw(uniq);
my $path = `pwd`;
chomp ($path);
my $workdir= $path; 
my %map = ();
my @genepheno=();
my $database="$path/GenePheno_Assoc.lst";
my $filename= "$path/Allgenes.lst";
open (my $file, $database);
my $gene = ();
my $genes = ();
my @allgenes =();
open (my $fh, '>>',"$filename")or die "Could not open file '$filename' $!";

while ( my $line =<$file>)
{
	
	if ($line =~ /(\S+)/)
	{
		$gene =$1;
		push @allgenes, $gene;	
	}
}
my @uniq_genes=uniq @allgenes;
foreach (@uniq_genes)
{
	print  $fh "$_\n";
}

my $x= @uniq_genes;
print "$x\n";

