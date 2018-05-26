
Skip to content

    Features
    Business
    Explore
    Marketplace
    Pricing

This repository
Sign in or Sign up

11
0

    0

bio-ontology-research-group/opa2vec
Code
Issues 0
Pull requests 0
Projects 0
Insights
opa2vec/AddAncestors.pl
2af04d6 on Apr 12
@fzohrasmaili fzohrasmaili Update AddAncestors.pl
60 lines (54 sloc) 1.2 KB

#!/usr/bin/perl
#use strict;
#Define all Needed Variables
use List::MoreUtils qw(uniq);
my $path = `pwd`;
my $annotfile=$ARGV[0];
chomp ($path);
#my $annotfile="$path/annotationAxiom.lst";
my $ancestfile= "$path/axioms.lst";
my $ontoclassesfile= "$path/classes.lst";
my $addoutfile= "$path/associationAxiomInferred.lst";
my @temparray=();
open (FILE, '>>', "$addoutfile");
open (FH,"$annotfile");
while (my $line =<FH>)
{
	chomp ($line);
	#$line =~ s/:/_/g;
	push @temparray, $line;
	open (FILE2, "$ontoclassesfile");
	while (my $secline=<FILE2>)
	{
		chomp ($secline);
		if ($line =~ /$secline /)
		{
			#push @temparray, my$line;
			my $childterm =$secline;
			open (FILE1, "$ancestfile");
			while (my $line2 =<FILE1>)
			{
				chomp ($line2);
				if ($line2 =~ /SubClassOf\((\S+)\s+(\S+)\)/)
				{
					#print ("Found one \n");
					my $checkgo=$1;
					my $Ancesterm=$2;
					if ($checkgo eq $childterm)
					{
						$line =~ s/$childterm/$Ancesterm/g;				
						#my$newline= "$prot hasFunction $goAnces\n";
						push @temparray, $line;
						#print ("Found two \n");
					}
				}
			}
		}		
	
	}
	
}
my @Finalarray= uniq @temparray;
foreach (@Finalarray)
{
 	my $final_annotation=$_;
	print FILE "$final_annotation\n";
}

  


